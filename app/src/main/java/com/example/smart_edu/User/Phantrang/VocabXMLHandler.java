package com.example.smart_edu.User.Phantrang;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class VocabXMLHandler extends DefaultHandler {

    boolean currentElement = false;
    String currentValue = "";

    String tumoi, nghia;
    vocab vocab;
    ArrayList<vocab> vocab_list;

    public ArrayList<vocab> getVocab_list(){
        return vocab_list;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = true;
        if (qName.equals("dictionary")){
            vocab_list = new ArrayList<vocab>();
        }
        else if (qName.equals("record")){
            vocab = new vocab();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentElement = false;
        if (qName.equalsIgnoreCase("word")){
            vocab.setWord(currentValue.trim());
        }
        else if (qName.equalsIgnoreCase("meaning")){
            vocab.setMeaning(currentValue.trim());
        }
        else if (qName.equalsIgnoreCase("record")){
            vocab_list.add(vocab);
        }
        currentValue = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentElement){
            currentValue = currentValue + new String(ch,start,length);
        }
    }
}
