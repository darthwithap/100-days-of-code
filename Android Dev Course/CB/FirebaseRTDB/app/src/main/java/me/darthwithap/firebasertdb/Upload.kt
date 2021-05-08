package me.darthwithap.firebasertdb

data class Upload(var name: String? = "", val url: String = "") {
    var key: String? = null

    init {
        if (name?.trim().isNullOrEmpty()) name = "No name"
    }
}
