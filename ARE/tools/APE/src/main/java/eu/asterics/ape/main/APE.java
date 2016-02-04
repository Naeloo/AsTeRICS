package eu.asterics.ape.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import eu.asterics.ape.packaging.Packager;
import eu.asterics.ape.parse.ModelInspector;
import eu.asterics.mw.are.exceptions.BundleManagementException;
import eu.asterics.mw.are.exceptions.ParseException;
import eu.asterics.mw.services.AstericsErrorHandling;
import eu.asterics.mw.services.ResourceRegistry;

import static eu.asterics.ape.main.APEProperties.*;

/*
 *    AsTeRICS - Assistive Technology Rapid Integration and Construction Set
 *
 *
 *        d8888      88888888888       8888888b.  8888888 .d8888b.   .d8888b.
 *       d88888          888           888   Y88b   888  d88P  Y88b d88P  Y88b
 *      d88P888          888           888    888   888  888    888 Y88b.    
 *     d88P 888 .d8888b  888   .d88b.  888   d88P   888  888         "Y888b. 
 *    d88P  888 88K      888  d8P  Y8b 8888888P"    888  888            "Y88b.
 *   d88P   888 "Y8888b. 888  88888888 888 T88b     888  888    888       "888
 *  d8888888888      X88 888  Y8b.     888  T88b    888  Y88b  d88P Y88b  d88P
 * d88P     888  88888P' 888   "Y8888  888   T88b 8888888 "Y8888P"   "Y8888P"
 *
 *
 *                    homepage: http://www.asterics.org
 *
 *     This project has been partly funded by the European Commission,
 *                      Grant Agreement Number 247730
 * 
 * 
 *    License: GPL v3.0 (GNU General Public License Version 3.0)
 *                 http://www.gnu.org/licenses/gpl.html
 *
 */

/**
 * This is the main class for the APE tool. Main tasks are configuring the tool with the set properties and starting the copying process.
 *         Author: martin.deinhofer@technikum-wien.at
 *         Date: Oct 30, 2015
 *         Time: 14:30:00 PM
 */

public class APE {
	private static final String TEMPLATE_DIR = "template";
	/**
	 * The properties of APE
	 */
	private APEProperties apeProperties=null;
	/**
	 * Reference to the ModelInspector instance.
	 */
	private ModelInspector modelInspector=null;
	/**
	 * Reference to the Packager instance.
	 */
	private Packager packager=null;
	private File projectDir;
	
	private static APE instance=null;
	
	/*
	 * Do some bootstrap initialization. 
	 */
	static {
		initLogger();
		initAPEBaseURI();
	}
	
	/**
	 * Launch the application.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws TransformerException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws URISyntaxException 
	 * @throws BundleManagementException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws IOException, ParseException, URISyntaxException, ParserConfigurationException, SAXException, TransformerException, BundleManagementException {
		try {
			APE.getInstance().start();
		} catch (APEConfigurationException e) {
			Notifier.error(e.getMessage(),null);
		}
	}
	
	/**
	 * Initilizes APE.baseURI with a 3-way fallback mechanism.
	 * 1) Use current working directory (CWD)
	 * 2) Use location of APE.jar
	 * 3) Use value of property APE.baseURI set as system property (-DAPE.baseURI=...) 
	 * @return
	 */
	private static URI initAPEBaseURI() {
		URI defaultAPEBaseURI=new File(".").toURI();
		Notifier.debug("Current working dir: "+defaultAPEBaseURI,null);
		try {
			defaultAPEBaseURI = ResourceRegistry.toPath(APE.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toUri();
			Notifier.debug("Location of APE.jar: "+defaultAPEBaseURI,null);
		} catch (URISyntaxException e) {
			Notifier.warning("Could not fetch default APE.baseURI", e);
		}
		
		Notifier.debug("SysProp["+APEProperties.P_APE_BASE_URI+"]="+System.getProperty(APEProperties.P_APE_BASE_URI),null);
		String newApeBaseURIString=System.getProperty(APEProperties.P_APE_BASE_URI);		
		if(newApeBaseURIString != null) {
			//Resolve against defaultApeBaseURI because if it is relative it should be resolved, if not it should be used absolutely
			setAPEBaseURI(defaultAPEBaseURI.resolve(newApeBaseURIString));
		} else {
			setAPEBaseURI(defaultAPEBaseURI);
		}
		return getAPEBaseURI();
	}
	
	/**
	 * Returns the singleton instance of APE.
	 * @return
	 */
	public static APE getInstance() {
		if(instance==null) {
			instance=new APE();
		}
		return instance;
	}

	/**
	 * Initializes and starts APE.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws TransformerException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws URISyntaxException 
	 * @throws BundleManagementException 
	 * @throws APEConfigurationException 
	 */
	public void start() throws IOException, ParseException, URISyntaxException, ParserConfigurationException, SAXException, TransformerException, BundleManagementException, APEConfigurationException {
		ResourceRegistry.getInstance().setOSGIMode(false);
		
		projectDir=ResourceRegistry.resolveRelativeFilePath(ResourceRegistry.toFile(getAPEBaseURI()), System.getProperty(APEProperties.P_APE_PROJECT_DIR,APEProperties.DEFAULT_PROJECT_DIR),false);
		APEProperties.APE_PROJECT_DIR_URI=projectDir.toURI();
		Notifier.info("Using ApeProp["+APEProperties.P_APE_PROJECT_DIR+"]="+APEProperties.APE_PROJECT_DIR_URI);
		
		if(!projectDir.exists()) {
			Notifier.info("Project folder does not exist, copying template to: "+projectDir);
			//If the projectDir does not exist, we should copy the template dir to the projectDir		
			FileUtils.copyDirectory(ResourceRegistry.resolveRelativeFilePath(getAPEBaseURI(), TEMPLATE_DIR), projectDir);
		}

		initProperties();

		String newAreBaseURIString=apeProperties.getProperty(APEProperties.P_ARE_BASE_URI);

		if(newAreBaseURIString!=null) {
			URI newAREBaseURI=ResourceRegistry.resolveRelativeFilePath(projectDir, newAreBaseURIString).toURI();
			Notifier.debug("ApeProp["+APEProperties.P_ARE_BASE_URI+"]="+newAREBaseURI,null);
			ResourceRegistry.getInstance().setAREBaseURI(newAREBaseURI);
		}

		modelInspector=new ModelInspector(apeProperties);
		packager=new Packager(apeProperties, modelInspector);
		packager.makeAll();
	}

	/**
	 * Initialize logger regarding level and format.
	 */
	private static void initLogger() {		
		Notifier.initLogger(System.getProperty(P_APE_LOG_LEVEL,DEFAULT_APE_LOG_LEVEL).toUpperCase());
	}
	
	/**
	 * Determins property file location (APE.properties), reads property values and overrides property values given as system property (-Dkey=value).
	 * @throws IOException 
	 */
	private void initProperties() throws IOException {
		Properties defaultProperties=new Properties();
		//Init with empty properties
		apeProperties=new APEProperties();

		try {
			//APE.properties is expected to be in the project directory
			URI apePropFileURI=ResourceRegistry.resolveRelativeFilePath(projectDir, "APE.properties").toURI();
			
			defaultProperties.load(new BufferedReader(new InputStreamReader(apePropFileURI.toURL().openStream())));
			Notifier.debug("defaultProperties: "+defaultProperties.toString(), null);
			apeProperties=new APEProperties(defaultProperties);
			for(Entry<Object, Object> entry : System.getProperties().entrySet()) {
				if(entry.getKey().toString().startsWith(APEProperties.APE_PROP_PREFIX)||entry.getKey().toString().startsWith(APEProperties.ARE_PROP_PREFIX)) {
					Notifier.info("Overriding ApeProp["+entry.getKey()+"]="+entry.getValue());
					apeProperties.setProperty(entry.getKey().toString(), entry.getValue().toString());
				}
			}
			//Now adding default models search path to APE.models property
			Notifier.debug("Adding bin/ARE/models as search path for model files to "+APEProperties.P_APE_MODELS,null);			
			apeProperties.setProperty(APEProperties.P_APE_MODELS,apeProperties.getProperty(APEProperties.P_APE_MODELS,"")+";bin/ARE/models");
			Notifier.info("Using ApeProp["+APEProperties.P_APE_MODELS+"]="+apeProperties.getProperty(APEProperties.P_APE_MODELS),null);
			Notifier.debug("Final apeProperties: "+apeProperties.toString(), null);
		} catch (IOException e) {
			Notifier.error("STOPPING: Initialization of APE properties failed", e);
			throw e;
		}
	}

	public APEProperties getApeProperties() {
		return apeProperties;
	}

	public void setApeProperties(APEProperties apeProperties) {
		this.apeProperties = apeProperties;
	}

	public static URI getAPEBaseURI() {
		return APEProperties.APE_BASE_URI;
	}

	/**
	 * Sets APE.baseURI to the given value. APE.baseURI will be used to fetch template data and lookup for the properties file APE.properties by default. 
	 * @param APEBaseURI
	 */
	public static void setAPEBaseURI(URI APEBaseURI) {
		APEProperties.APE_BASE_URI = APEBaseURI;
		Notifier.info("Setting APE base URI to <"+APEBaseURI+">");		
	}
	
	/**
	 * The {@link} ModelInspector} object to analyze an AsTeRICS model.
	 * @return
	 */
	public ModelInspector getModelInspector() {
		return modelInspector;
	}

	public void setModelInspector(ModelInspector modelInspector) {
		this.modelInspector = modelInspector;
	}

	/**
	 * Returns a reference to the Packager that actually does all the copying of resources.
	 * @return
	 */
	public Packager getPackager() {
		return packager;
	}

	public void setPackager(Packager packager) {
		this.packager = packager;
	}
}
