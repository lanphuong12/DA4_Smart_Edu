package com.example.smart_edu.User.Phantrang;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_edu.R;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Vocab_main extends AppCompatActivity implements vocab_adapter.OnVocabListener {

    private RecyclerView recyclerView;
    private vocab_adapter vocabAdapter;
    private ArrayList<vocab> listVocab;

    private ArrayList<vocab> ListTumoi;
    vocab tumoi = new vocab();


    private boolean isLoading;
    private boolean isLastPage;
    private int totalPage = 3;
    private int curentPage = 1;

    TextToSpeech textToSpeech;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_vocab_main1);

        ListTumoi =readingXML();
        Anhxa();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        setFirstData();

        recyclerView.addOnScrollListener(new PaginatonScroll(linearLayoutManager) {
            @Override
            public void loadMoreItem() {
                isLoading = true;
                curentPage += 1;
                loadTextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });

        ArrayList<vocab> listSearch = readingXML();
        ArrayList<String> listWordSearch = new ArrayList<>();
        for (int i=0; i< listSearch.size(); i++){
            listWordSearch.add(listSearch.get(i).getWord());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listWordSearch);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                for (int i=0; i< listSearch.size(); i++){
                    if (listSearch.get(i).getWord().equals(item)){
                        Dialog dialog=new Dialog(Vocab_main.this);
                        dialog.setContentView(R.layout.user_vocab_display);
                        final TextView tumoi = dialog.findViewById(R.id.tv_word_eng);
                        final TextView nghia = dialog.findViewById(R.id.tv_meaning_eng);
                        final ImageButton sound = dialog.findViewById(R.id.bt_sound_word_eng);

                        tumoi.setText(listSearch.get(i).getWord());
                        nghia.setText(listSearch.get(i).getMeaning());
                        sound.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Doc(tumoi.getText().toString());
                            }
                        });

                        dialog.show();
                        break;
                    }
                }
            }
        });
    }

    public void DialogWord(int position, ArrayList<vocab> listView){

        Dialog dialog=new Dialog(Vocab_main.this);
        dialog.setContentView(R.layout.user_vocab_display);
        final TextView tumoi = dialog.findViewById(R.id.tv_word_eng);
        final TextView nghia = dialog.findViewById(R.id.tv_meaning_eng);
        final ImageButton sound = dialog.findViewById(R.id.bt_sound_word_eng);

        tumoi.setText(listView.get(position).getWord());
        nghia.setText(listView.get(position).getMeaning());
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doc(tumoi.getText().toString());
            }
        });

        dialog.show();
    }

    //Load data page 1
    private void setFirstData() {

        listVocab = getListVocab();
        vocabAdapter.setData(listVocab);

        if (curentPage < totalPage) {
            vocabAdapter.addLoading();
        } else {
            isLastPage = true;
        }
    }

    private ArrayList<vocab> getListVocab() {
        Toast.makeText(this, "Load data page " + curentPage, Toast.LENGTH_SHORT).show();
//        ArrayList<vocab> list = new ArrayList<>();
//        for (int i = 0; i< 15; i++){
//            list.add(new vocab("vocab","meaning"));
//        }

        ArrayList<vocab> list = new ArrayList<>();
        ArrayList<vocab> listRemove = new ArrayList<>();

        for (int i= 0; i< ListTumoi.size();i++){
            if (i>= 20){
                break;
            }
            list.add(ListTumoi.get(i));
            listRemove.add(ListTumoi.get(i));

        }
        ListTumoi.removeAll(listRemove);
        return list;
    }

    private ArrayList<vocab> readingXML() {
        ArrayList<vocab> vocabArrayList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            InputStream is = getAssets().open("eng_vn.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            vocabArrayList = processParsing(parser);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vocabArrayList;
    }

    private ArrayList<vocab> processParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<vocab> vocabArrayList = new ArrayList<>();
        int evenType = parser.getEventType();
        vocab currentVocab = null;
        int i = 0;
        while (evenType != XmlPullParser.END_DOCUMENT) {
            String edtname = null;
            switch (evenType) {
                case XmlPullParser.START_TAG:
                    edtname = parser.getName();
                    if ("record".equals(edtname)) {
                        currentVocab = new vocab();
                        vocabArrayList.add(currentVocab);
                    } else if (currentVocab != null) {
                        if ("word".equals(edtname)) {
                            currentVocab.setWord(parser.nextText());
                        } else if ("meaning".equals(edtname)) {
                            currentVocab.setMeaning(parser.nextText());
                        }
                    }
                    break;

            }
            evenType = parser.next();
        }
        return vocabArrayList;
        //PrintPlayer(vocabArrayList);
    }

    private void PrintPlayer(ArrayList<vocab> vocabArrayList) {
        LinearLayout linearLayout = findViewById(R.id.LinearLayout);

        int i = 0;
        for (vocab tumoi : vocabArrayList) {
            StringBuilder builder = new StringBuilder();
            TextView textView = new TextView(this);
            builder.append(tumoi.getWord()).append("\n")
                    .append("\t\t" + tumoi.getMeaning()).append("\n\n");
            textView.setText(builder);
            linearLayout.addView(textView);
            i++;
            if (i >= 1000) {
                break;
            }

        }

    }

//    private ArrayList<vocab> parseXML() {
//        ArrayList<vocab> vocabArrayList = new ArrayList<>();
//        AssetManager assetManager = getBaseContext().getAssets();
//        try {
//            InputStream is = assetManager.open("eng_vn.xml");
//            SAXParserFactory spf = SAXParserFactory.newInstance();
//            SAXParser sp = spf.newSAXParser();
//            XMLReader xr = sp.getXMLReader();
//            VocabXMLHandler myXMLHandler = new VocabXMLHandler();
//            xr.setContentHandler(myXMLHandler);
//            InputSource inStream = new InputSource(is);
//            xr.parse(inStream);
//
////            LinearLayout linearLayout = findViewById(R.id.LinearLayout);
////            TextView tv_word = findViewById(R.id.tv_user_vocab);
////            TextView tv_meaning = findViewById(R.id.tv_user_meaning);
//            vocabArrayList = myXMLHandler.getVocab_list();
////            for (vocab tumoi : vocabArrayList){
////                tv_word.setText(tumoi.getWord());
////                linearLayout.addView(tv_word);
////                tv_meaning.setText(tumoi.getMeaning());
////                linearLayout.addView(tv_meaning);
////            }
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return vocabArrayList;
//    }

    private void loadTextPage() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<vocab> list = getListVocab();

                vocabAdapter.removeLoading();
                listVocab.addAll(list);
                vocabAdapter.notifyDataSetChanged();

                isLoading = false;

                if (curentPage < totalPage) {
                    vocabAdapter.addLoading();
                } else {
                    isLastPage = true;
                }
            }
        }, 5000);
    }

    private void Anhxa() {
        recyclerView = findViewById(R.id.recycler_view_vocab);

        vocabAdapter = new vocab_adapter(listVocab, this);
        recyclerView.setAdapter(vocabAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        autoCompleteTextView = findViewById(R.id.edt_search_word);
    }


    @Override
    public void OnVocabClick(int position) {

        vocab Word = listVocab.get(position);
//        LayoutInflater inflater = getLayoutInflater();
//        View alertLayout = inflater.inflate(R.layout.user_vocab_display, null);
//        final TextView tumoi = (TextView) findViewById(R.id.tv_word_eng);
//        final TextView nghia = (TextView) findViewById(R.id.tv_meaning_eng);
//        final ImageButton sound = (ImageButton) findViewById(R.id.bt_sound_word_eng);
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(Vocab_main.this);
//        alert.setView(alertLayout);
//        alert.setCancelable(false);
//
//        tumoi.setText(Word.getWord());
//        nghia.setText(Word.getMeaning());
//        sound.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Doc(tumoi.getText().toString());
//            }
//        });
//        AlertDialog dialog = alert.create();
//        dialog.show();
        Dialog dialog=new Dialog(Vocab_main.this);
        dialog.setContentView(R.layout.user_vocab_display);
        final TextView tumoi = dialog.findViewById(R.id.tv_word_eng);
        final TextView nghia = dialog.findViewById(R.id.tv_meaning_eng);
        final ImageButton sound = dialog.findViewById(R.id.bt_sound_word_eng);

        tumoi.setText(Word.getWord());
        nghia.setText(Word.getMeaning());
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doc(tumoi.getText().toString());
            }
        });

        dialog.show();
    }


    //Phuong thức đọc câu hỏi từ text View
    public void Doc(String word) {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                } else {
                    Toast.makeText(Vocab_main.this, "Error!", Toast.LENGTH_LONG).show();
                }
                if (word != null) {
                    textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null);
                }
                //Dừng dọc câu hỏii khi back lạii
                textToSpeech.playSilence(2000, TextToSpeech.QUEUE_ADD, null);
            }
        });
    }

//    private void ReadXML() {
//        try {
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            SAXParser saxParser = factory.newSAXParser();
//            DefaultHandler handler = new DefaultHandler(){
//                String curentValue = "";
//                boolean curentElement = false;
//
//                @Override
//                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//                    curentElement = true;
//                    curentValue = "";
//                    if (localName.equals("record")){
//                        vocab = new HashMap<>();
//                    }
//                }
//
//                @Override
//                public void endElement(String uri, String localName, String qName) throws SAXException {
//                    curentElement = false;
//                    if (localName.equalsIgnoreCase("word")){
//                        vocab.put("word", curentValue);
//                    }
//                    else if (localName.equalsIgnoreCase("meaning")){
//                        vocab.put("meaning", curentValue);
//                    }
//                    else if (localName.equalsIgnoreCase("record")){
//                        vocab_list.add(vocab);
//                    }
//                }
//
//                @Override
//                public void characters(char[] ch, int start, int length) throws SAXException {
//                    if (curentElement){
//                        curentValue = curentValue + new String(ch, start, length);
//                    }
//                }//end of characters method
//            };//end of DefaultHandler object
//
//            InputStream is = getAssets().open("eng_vn.xml");
//            saxParser.parse(is, handler);
//
////            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
////            for (int i = 0; i <= 100; i++){
////                arrayList.add(vocab_list.get(i));
////                i++;
////            }
//
//            ListAdapter adapter = new SimpleAdapter(com.example.smart_edu.User.Vocab.Vocab_main.this, vocab_list, R.layout.user_vocab_adapter, new String[]{"word"}, new int[]{R.id.tv_user_vocab});
//            lv_vocab.setAdapter(adapter);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

