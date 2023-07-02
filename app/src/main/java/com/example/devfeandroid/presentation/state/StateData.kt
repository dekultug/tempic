package com.example.devfeandroid.presentation.state

sealed class StateData<Data>(

    var data: Data? = null,

    var message: String? = null,


    var status: STATE_TYPE,

    var exception: Throwable? = null,
) : IStateData {

    class Init<Data> : StateData<Data>(null, null, STATE_TYPE.INIT)

    class Loading<Data>(message: String? = null) : StateData<Data>(message = message, status = STATE_TYPE.LOADING)

    class Error<Data>(
        message: String? = null,
        data: Data? = null,
        throwable: Throwable
    ) : StateData<Data>(
        message = message,
        data = data,
        status = STATE_TYPE.ERROR,
        exception = throwable
    )

    class Success<Data>(data: Data?) : StateData<Data>(data = data, status = STATE_TYPE.SUCCESS)

}


