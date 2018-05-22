package sos.rock.sosapp.Main;

import android.view.MenuItem;

import sos.rock.sosapp.BaseActivity;


public class BaseTitleActivity extends BaseActivity {

    protected  void InitToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
    }

    protected void SetToolbarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            BackToParent();
        }
        return true;
    }

    protected void BackToParent() {

    }

    @Override
    public void onBackPressed() {
        BackToParent();
    }
}
