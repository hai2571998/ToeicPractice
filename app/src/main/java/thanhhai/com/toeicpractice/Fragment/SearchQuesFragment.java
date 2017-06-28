package thanhhai.com.toeicpractice.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import thanhhai.com.toeicpractice.Database.QuestionAdapter;
import thanhhai.com.toeicpractice.Database.QuestionController;
import thanhhai.com.toeicpractice.MainActivity;
import thanhhai.com.toeicpractice.R;

public class SearchQuesFragment extends Fragment {

    ListView lvQuestion;
    QuestionController questionController;
    QuestionAdapter adapter;
    EditText edtSearch;
    ImageButton imgSubject;
    String subject = "";

    public SearchQuesFragment() {

    }

    //goi mo database ch nao
    public void begin() {
        lvQuestion = (ListView) getActivity().findViewById(R.id.lvQuestion);
        edtSearch = (EditText) getActivity().findViewById(R.id.edtSearch);
        imgSubject = (ImageButton) getActivity().findViewById(R.id.imgSubject);
        questionController = new QuestionController(getActivity());
        listCursor(questionController.getSearchQuestion(subject, edtSearch.getText().toString()));
    }

    public void listCursor(Cursor cursor) {
        adapter = new QuestionAdapter(getActivity(), cursor, true);
        lvQuestion.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Tìm kiếm");
        return inflater.inflate(R.layout.fragment_search_ques, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        begin();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listCursor(questionController.getSearchQuestion(subject, edtSearch.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

    }

    public void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.questall:
                        subject = "";
                        listCursor(questionController.getQuestionBySubject(subject));
                        break;
                    case R.id.qdcd:
                        subject = "socap";
                        listCursor(questionController.getQuestionBySubject(subject));
                        break;
                    case R.id.qhoa:
                        listCursor(questionController.getQuestionBySubject(subject));
                        break;
                    case R.id.qtoan:
                        subject = "toan";
                        listCursor(questionController.getQuestionBySubject(subject));
                        break;
                }
                return false;
            }
        });
        popupMenu.inflate(R.menu.menu_question);
        setForceShowIcon(popupMenu);
        popupMenu.show();
    }


    //Hiện thị icon trên popupMenu Field
    //import java.lang.reflect.Field;
    //import java.lang.reflect.Method;
    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
