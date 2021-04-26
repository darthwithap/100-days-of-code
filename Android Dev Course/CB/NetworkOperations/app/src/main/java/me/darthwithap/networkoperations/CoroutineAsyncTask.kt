package me.darthwithap.networkoperations

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class CoroutineAsyncTask<Params, Progress, Result> {
    open fun onPreExecute() {}

    abstract fun doInBackground(vararg params: Params?) : Result

    open fun onProgressUpdate(vararg progress: Progress?) {}

    open fun onPostExecute(result: Result?) {}

    open fun onCancelled(result: Result?) {}

    fun execute(vararg params: Params?) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = doInBackground(*params)
            withContext(Dispatchers.Main) {
                onPostExecute(result)
            }
        }
    }

    fun cancel(mayInterruptIfRunning: Boolean) {}

    protected fun publishProgress(vararg progress: Progress?) {
        GlobalScope.launch(Dispatchers.Main) {
            onProgressUpdate(*progress)
        }
    }
}