package com.easyinc.autocomplete.presentaion

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easyinc.autocomplete.model.UpdatedText
import com.easyinc.autocomplete.utils.Logger
import com.easyinc.autocomplete.utils.extentions.returnIfExcist
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class AutoCompleteViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val watcherLiveData = MutableLiveData<UpdatedText>()
    val matchWordsLiveData = MutableLiveData<String>()


    fun updateText(watcher: String, list: List<String>){

        val lastIndex = watcher.lastOrNull() ?: '0'

        if (lastIndex.isWhitespace()){

            val d= list.returnIfExcist(watcher.trimEnd().split(' ').last())
            if (d == null){
                val formatString = watcher.trimEnd().dropLastWhile { it.isLetter() }
                watcherLiveData.postValue(
                    UpdatedText(
                        formatString,
                        true
                    )
                )
            }else{
                val k = watcher.trimEnd().dropLastWhile { it.isLetter() } + d
                watcherLiveData.postValue(
                    UpdatedText(
                        k,
                        false
                    )
                )
            }

        }


    }

    fun observeMatchesWords(word: String){

        compositeDisposable.add(
            Observable.create<String> {
                it.onNext(word)
            }.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        matchWordsLiveData.postValue(it)
                    },{
                        Logger.dt("Error is: ${it.localizedMessage}")
                    }
                )
        )

    }

}