package thanhhai.com.toeicpractice.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import thanhhai.com.toeicpractice.HomeModel.GrammarActivity;
import thanhhai.com.toeicpractice.R;

public class GrammarFragment extends Fragment {

    ListView lvGrammar;
    ProgressDialog progressDialog;
    ArrayList<String> mListTitle;

    ArrayList<String> arr = new ArrayList();
    Document doc = null;

    public static GrammarFragment newInstance() {
        GrammarFragment tricksFragment = new GrammarFragment();
        return tricksFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grammar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvGrammar = (ListView) getActivity().findViewById(R.id.lvGrammar);
        new GrammarAsyncTask().execute(100000);
        lvGrammar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = mListTitle.get(position);
                String nameFile = String.valueOf(position+1);
                Intent intent = new Intent(view.getContext(), GrammarActivity.class);
                intent.putExtra("nameFile",nameFile);
                intent.putExtra("titleFile", title);
                startActivity(intent);
            }
        });
    }

    private class GrammarAsyncTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            getTitle();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mListTitle = new ArrayList();
            mListTitle = arr;
            ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.grammar_item, arr);
            lvGrammar.setAdapter(adapter);
        }
    }

    public void getTitle() {
        AssetManager am = getContext().getAssets();
        String htmlContentInStringFormat = null;
        for (int i = 1; i <= 50; i++) {
            try {
                InputStream is = am.open("grammar/" + i + ".htm", AssetManager.ACCESS_BUFFER);
                htmlContentInStringFormat = StreamToString(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            doc = Jsoup.parse(htmlContentInStringFormat);
            Elements elements = doc.select("h3");
            arr.add(elements.get(0).text());
        }
    }
    public static String StreamToString(InputStream is){
        if(is == null){
            return "";
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1){
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
