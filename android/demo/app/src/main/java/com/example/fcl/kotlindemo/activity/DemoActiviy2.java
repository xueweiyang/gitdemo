package com.example.fcl.kotlindemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;
import com.example.fcl.kotlindemo.R;
import com.example.fcl.kotlindemo.adapter.ResultAdapter;
import com.example.fcl.kotlindemo.view.SearchCardView;

public class DemoActiviy2 extends BaseActivity {
private Toolbar toolbar;
private RecyclerView recyclerView;
private SearchCardView searchView;
private ResultAdapter resultAdapter;
private EditText et;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initView();
    }

    private void initView() {
        toolbar=findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.resultRecycler);
        searchView=findViewById(R.id.searchCard);
        et = searchView.getEt();
        setRecycler();
        setSearch();
    }

    private void setSearch() {
       et.setText("kotlin");
       et.setSelection(et.getText().length());
       et.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });
       et.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });
    }

    private void setRecycler() {
        resultAdapter = new ResultAdapter(this);
        recyclerView.setAdapter(resultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
