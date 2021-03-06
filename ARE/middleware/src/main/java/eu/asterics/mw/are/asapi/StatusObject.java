/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package eu.asterics.mw.are.asapi;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusObject
        implements org.apache.thrift.TBase<StatusObject, StatusObject._Fields>, java.io.Serializable, Cloneable {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
            "StatusObject");

    private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField(
            "status", org.apache.thrift.protocol.TType.STRING, (short) 1);
    private static final org.apache.thrift.protocol.TField INVOLVED_COMPONENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField(
            "involvedComponentID", org.apache.thrift.protocol.TType.STRING, (short) 2);
    private static final org.apache.thrift.protocol.TField ERROR_MSG_FIELD_DESC = new org.apache.thrift.protocol.TField(
            "errorMsg", org.apache.thrift.protocol.TType.STRING, (short) 3);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new StatusObjectStandardSchemeFactory());
        schemes.put(TupleScheme.class, new StatusObjectTupleSchemeFactory());
    }

    public String status; // required
    public String involvedComponentID; // required
    public String errorMsg; // required

    /**
     * The set of fields this struct contains, along with convenience methods
     * for finding and manipulating them.
     */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        STATUS((short) 1, "status"), INVOLVED_COMPONENT_ID((short) 2, "involvedComponentID"), ERROR_MSG((short) 3,
                "errorMsg");

        private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

        static {
            for (_Fields field : EnumSet.allOf(_Fields.class)) {
                byName.put(field.getFieldName(), field);
            }
        }

        /**
         * Find the _Fields constant that matches fieldId, or null if its not
         * found.
         */
        public static _Fields findByThriftId(int fieldId) {
            switch (fieldId) {
            case 1: // STATUS
                return STATUS;
            case 2: // INVOLVED_COMPONENT_ID
                return INVOLVED_COMPONENT_ID;
            case 3: // ERROR_MSG
                return ERROR_MSG;
            default:
                return null;
            }
        }

        /**
         * Find the _Fields constant that matches fieldId, throwing an exception
         * if it is not found.
         */
        public static _Fields findByThriftIdOrThrow(int fieldId) {
            _Fields fields = findByThriftId(fieldId);
            if (fields == null)
                throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
            return fields;
        }

        /**
         * Find the _Fields constant that matches name, or null if its not
         * found.
         */
        public static _Fields findByName(String name) {
            return byName.get(name);
        }

        private final short _thriftId;
        private final String _fieldName;

        _Fields(short thriftId, String fieldName) {
            _thriftId = thriftId;
            _fieldName = fieldName;
        }

        public short getThriftFieldId() {
            return _thriftId;
        }

        public String getFieldName() {
            return _fieldName;
        }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
                _Fields.class);
        tmpMap.put(_Fields.STATUS,
                new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT,
                        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.INVOLVED_COMPONENT_ID,
                new org.apache.thrift.meta_data.FieldMetaData("involvedComponentID",
                        org.apache.thrift.TFieldRequirementType.DEFAULT,
                        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.ERROR_MSG,
                new org.apache.thrift.meta_data.FieldMetaData("errorMsg",
                        org.apache.thrift.TFieldRequirementType.DEFAULT,
                        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StatusObject.class, metaDataMap);
    }

    public StatusObject() {
    }

    public StatusObject(String status, String involvedComponentID, String errorMsg) {
        this();
        this.status = status;
        this.involvedComponentID = involvedComponentID;
        this.errorMsg = errorMsg;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public StatusObject(StatusObject other) {
        if (other.isSetStatus()) {
            this.status = other.status;
        }
        if (other.isSetInvolvedComponentID()) {
            this.involvedComponentID = other.involvedComponentID;
        }
        if (other.isSetErrorMsg()) {
            this.errorMsg = other.errorMsg;
        }
    }

    public StatusObject deepCopy() {
        return new StatusObject(this);
    }

    @Override
    public void clear() {
        this.status = null;
        this.involvedComponentID = null;
        this.errorMsg = null;
    }

    public String getStatus() {
        return this.status;
    }

    public StatusObject setStatus(String status) {
        this.status = status;
        return this;
    }

    public void unsetStatus() {
        this.status = null;
    }

    /**
     * Returns true if field status is set (has been assigned a value) and false
     * otherwise
     */
    public boolean isSetStatus() {
        return this.status != null;
    }

    public void setStatusIsSet(boolean value) {
        if (!value) {
            this.status = null;
        }
    }

    public String getInvolvedComponentID() {
        return this.involvedComponentID;
    }

    public StatusObject setInvolvedComponentID(String involvedComponentID) {
        this.involvedComponentID = involvedComponentID;
        return this;
    }

    public void unsetInvolvedComponentID() {
        this.involvedComponentID = null;
    }

    /**
     * Returns true if field involvedComponentID is set (has been assigned a
     * value) and false otherwise
     */
    public boolean isSetInvolvedComponentID() {
        return this.involvedComponentID != null;
    }

    public void setInvolvedComponentIDIsSet(boolean value) {
        if (!value) {
            this.involvedComponentID = null;
        }
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public StatusObject setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public void unsetErrorMsg() {
        this.errorMsg = null;
    }

    /**
     * Returns true if field errorMsg is set (has been assigned a value) and
     * false otherwise
     */
    public boolean isSetErrorMsg() {
        return this.errorMsg != null;
    }

    public void setErrorMsgIsSet(boolean value) {
        if (!value) {
            this.errorMsg = null;
        }
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
        case STATUS:
            if (value == null) {
                unsetStatus();
            } else {
                setStatus((String) value);
            }
            break;

        case INVOLVED_COMPONENT_ID:
            if (value == null) {
                unsetInvolvedComponentID();
            } else {
                setInvolvedComponentID((String) value);
            }
            break;

        case ERROR_MSG:
            if (value == null) {
                unsetErrorMsg();
            } else {
                setErrorMsg((String) value);
            }
            break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
        case STATUS:
            return getStatus();

        case INVOLVED_COMPONENT_ID:
            return getInvolvedComponentID();

        case ERROR_MSG:
            return getErrorMsg();

        }
        throw new IllegalStateException();
    }

    /**
     * Returns true if field corresponding to fieldID is set (has been assigned
     * a value) and false otherwise
     */
    public boolean isSet(_Fields field) {
        if (field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {
        case STATUS:
            return isSetStatus();
        case INVOLVED_COMPONENT_ID:
            return isSetInvolvedComponentID();
        case ERROR_MSG:
            return isSetErrorMsg();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof StatusObject)
            return this.equals((StatusObject) that);
        return false;
    }

    public boolean equals(StatusObject that) {
        if (that == null)
            return false;

        boolean this_present_status = true && this.isSetStatus();
        boolean that_present_status = true && that.isSetStatus();
        if (this_present_status || that_present_status) {
            if (!(this_present_status && that_present_status))
                return false;
            if (!this.status.equals(that.status))
                return false;
        }

        boolean this_present_involvedComponentID = true && this.isSetInvolvedComponentID();
        boolean that_present_involvedComponentID = true && that.isSetInvolvedComponentID();
        if (this_present_involvedComponentID || that_present_involvedComponentID) {
            if (!(this_present_involvedComponentID && that_present_involvedComponentID))
                return false;
            if (!this.involvedComponentID.equals(that.involvedComponentID))
                return false;
        }

        boolean this_present_errorMsg = true && this.isSetErrorMsg();
        boolean that_present_errorMsg = true && that.isSetErrorMsg();
        if (this_present_errorMsg || that_present_errorMsg) {
            if (!(this_present_errorMsg && that_present_errorMsg))
                return false;
            if (!this.errorMsg.equals(that.errorMsg))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int compareTo(StatusObject other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;
        StatusObject typedOther = (StatusObject) other;

        lastComparison = Boolean.valueOf(isSetStatus()).compareTo(typedOther.isSetStatus());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetStatus()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, typedOther.status);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetInvolvedComponentID()).compareTo(typedOther.isSetInvolvedComponentID());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetInvolvedComponentID()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.involvedComponentID,
                    typedOther.involvedComponentID);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetErrorMsg()).compareTo(typedOther.isSetErrorMsg());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetErrorMsg()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errorMsg, typedOther.errorMsg);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        return 0;
    }

    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
        schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
        schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StatusObject(");
        boolean first = true;

        sb.append("status:");
        if (this.status == null) {
            sb.append("null");
        } else {
            sb.append(this.status);
        }
        first = false;
        if (!first)
            sb.append(", ");
        sb.append("involvedComponentID:");
        if (this.involvedComponentID == null) {
            sb.append("null");
        } else {
            sb.append(this.involvedComponentID);
        }
        first = false;
        if (!first)
            sb.append(", ");
        sb.append("errorMsg:");
        if (this.errorMsg == null) {
            sb.append("null");
        } else {
            sb.append(this.errorMsg);
        }
        first = false;
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        try {
            write(new org.apache.thrift.protocol.TCompactProtocol(
                    new org.apache.thrift.transport.TIOStreamTransport(out)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        try {
            read(new org.apache.thrift.protocol.TCompactProtocol(
                    new org.apache.thrift.transport.TIOStreamTransport(in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private static class StatusObjectStandardSchemeFactory implements SchemeFactory {
        public StatusObjectStandardScheme getScheme() {
            return new StatusObjectStandardScheme();
        }
    }

    private static class StatusObjectStandardScheme extends StandardScheme<StatusObject> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, StatusObject struct)
                throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                case 1: // STATUS
                    if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                        struct.status = iprot.readString();
                        struct.setStatusIsSet(true);
                    } else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 2: // INVOLVED_COMPONENT_ID
                    if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                        struct.involvedComponentID = iprot.readString();
                        struct.setInvolvedComponentIDIsSet(true);
                    } else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                case 3: // ERROR_MSG
                    if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                        struct.errorMsg = iprot.readString();
                        struct.setErrorMsgIsSet(true);
                    } else {
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    break;
                default:
                    org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

            // check for required fields of primitive type, which can't be
            // checked in the validate method
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot, StatusObject struct)
                throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.status != null) {
                oprot.writeFieldBegin(STATUS_FIELD_DESC);
                oprot.writeString(struct.status);
                oprot.writeFieldEnd();
            }
            if (struct.involvedComponentID != null) {
                oprot.writeFieldBegin(INVOLVED_COMPONENT_ID_FIELD_DESC);
                oprot.writeString(struct.involvedComponentID);
                oprot.writeFieldEnd();
            }
            if (struct.errorMsg != null) {
                oprot.writeFieldBegin(ERROR_MSG_FIELD_DESC);
                oprot.writeString(struct.errorMsg);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class StatusObjectTupleSchemeFactory implements SchemeFactory {
        public StatusObjectTupleScheme getScheme() {
            return new StatusObjectTupleScheme();
        }
    }

    private static class StatusObjectTupleScheme extends TupleScheme<StatusObject> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, StatusObject struct)
                throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetStatus()) {
                optionals.set(0);
            }
            if (struct.isSetInvolvedComponentID()) {
                optionals.set(1);
            }
            if (struct.isSetErrorMsg()) {
                optionals.set(2);
            }
            oprot.writeBitSet(optionals, 3);
            if (struct.isSetStatus()) {
                oprot.writeString(struct.status);
            }
            if (struct.isSetInvolvedComponentID()) {
                oprot.writeString(struct.involvedComponentID);
            }
            if (struct.isSetErrorMsg()) {
                oprot.writeString(struct.errorMsg);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, StatusObject struct)
                throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(3);
            if (incoming.get(0)) {
                struct.status = iprot.readString();
                struct.setStatusIsSet(true);
            }
            if (incoming.get(1)) {
                struct.involvedComponentID = iprot.readString();
                struct.setInvolvedComponentIDIsSet(true);
            }
            if (incoming.get(2)) {
                struct.errorMsg = iprot.readString();
                struct.setErrorMsgIsSet(true);
            }
        }
    }

}
