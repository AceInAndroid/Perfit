package com.mperfit.perfit.di.component;

import android.app.Activity;

import com.mperfit.perfit.di.FragmentScope;
import com.mperfit.perfit.di.module.FragmentModule;
import com.mperfit.perfit.ui.activities.fragment.ActivityFragment;
import com.mperfit.perfit.ui.article.fragment.FitnessFragment;
import com.mperfit.perfit.ui.article.fragment.SportsFragment;
import com.mperfit.perfit.ui.classes.fragment.ClassFragment;
import com.mperfit.perfit.ui.competition.fragment.CompetitionActivityFragment;
import com.mperfit.perfit.ui.competition.fragment.GameFragment;
import com.mperfit.perfit.ui.home.fragment.HomeFragment;
import com.mperfit.perfit.ui.me.fragment.PersonalFragment;
import com.mperfit.perfit.ui.me.fragment.myjoined.MyLikedFragment;
import com.mperfit.perfit.ui.community.fragment.SquareFragment;
import com.mperfit.perfit.ui.newhome.fragment.NewHomeFragment;
import com.mperfit.perfit.ui.personal.fragment.NewPersonalFragment;

import dagger.Component;

/**
 * Created by zhangbing on 16/10/12.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();


    void inject(ActivityFragment activityFragment);
    void inject(PersonalFragment personalFragment);
    void inject(MyLikedFragment myLikedFragment);
    void inject(HomeFragment homeFragment);
    void inject(FitnessFragment fitnessFragment);
    void inject(SportsFragment fitnessFragment);
    void inject(ClassFragment classFragment);
    void inject(SquareFragment squareFragment);
    void inject(NewPersonalFragment newPersonalFragment);
    void inject(NewHomeFragment newHomeFragment);
    void inject(GameFragment gameFragment);
    void inject(CompetitionActivityFragment competitionActivityFragment);



}
