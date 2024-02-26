package com.ms.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.reflect.ReflectData;

import java.io.File;
import java.io.IOException;

public class Email {

    // Getter and Setter methods
    // We have to manually define that which one to keep default or something
    private String sender;
    private String recipient;
    private String subject;
    private String body;

    // Constructor
    public Email() {
    }

    // Generate Avro schema from Email class
    public Schema generateSchema() {
        return ReflectData.get().getSchema(Email.class);
    }

    // Serialize Email object using Avro and store it on disk
    public  void serializeEmail(Email email, String fileName) throws IOException {
        Schema schema = generateSchema();
        GenericRecord avroRecord = new GenericData.Record(schema);
        //
        avroRecord.put("sender", email.getSender());
        avroRecord.put("recipient", email.getRecipient());
        avroRecord.put("subject", email.getSubject());
        avroRecord.put("body", email.getBody());

        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(new GenericDatumWriter<>(schema))) {
            dataFileWriter.create(avroRecord.getSchema(), new File(fileName));
            dataFileWriter.append(avroRecord);
        }
    }

    public  Email deserialize(String fileName) throws IOException {
        Schema schema = ReflectData.get().getSchema(Email.class);
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(new File(fileName), datumReader)) {
            GenericRecord emailRecord = dataFileReader.next();
            Email email = new Email();
            email.setSender(emailRecord.get("sender").toString());
            email.setRecipient(emailRecord.get("recipient").toString());
            email.setSubject(emailRecord.get("subject").toString());
            email.setBody(emailRecord.get("body").toString());
            return email;
        }
    }

    public static void main(String[] args) {
        System.out.println("This is a email class");
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}

