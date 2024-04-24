import androidx.lifecycle.*

// ViewModel для управления данными и бизнес-логикой
class MainView : ViewModel() {
    // Хранение введенного значения
    private val _inputValue = MutableLiveData<String>()

    // Публичное поле для наблюдения из активности
    val result: LiveData<String> = Transformations.map(_inputValue) { "Entered value: $it" }

    // Установка введенного значения
    fun setInputValue(value: String) {
        _inputValue.value = value
    }
}