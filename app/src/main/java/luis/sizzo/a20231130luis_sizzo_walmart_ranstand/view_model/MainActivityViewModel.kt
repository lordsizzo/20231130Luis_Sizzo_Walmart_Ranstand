package luis.sizzo.a20231130luis_sizzo_walmart_ranstand.view_model

import androidx.lifecycle.*
import kotlinx.coroutines.*
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.UIStateFlow
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.model.repository.RepositoryImpl

class MainActivityViewModel: ViewModel() {

    private val repository = RepositoryImpl()

    private val _countriesResponse = MutableLiveData<UIStateFlow>()
    val getCoutriesResponse: MutableLiveData<UIStateFlow>
        get() = _countriesResponse

    private val _stateView = MutableLiveData<Boolean>()
    val stateView: MutableLiveData<Boolean>
        get() = _stateView

    fun getCountries(){
        viewModelScope.launch {
            repository.getCountries().collect {
                _countriesResponse.postValue(it)
            }
        }
    }
    fun getStateView(state: Boolean){
        _stateView.value = state
    }
}