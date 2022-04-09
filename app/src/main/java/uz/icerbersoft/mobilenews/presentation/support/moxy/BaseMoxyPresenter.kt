package uz.icerbersoft.mobilenews.presentation.support.moxy

import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BaseMoxyPresenter<View : MvpView> : MvpPresenter<View>() {

    var compositeDisposable = CompositeDisposable()
        private set

    final fun clearPresenter(){
        compositeDisposable.dispose()
    }
}