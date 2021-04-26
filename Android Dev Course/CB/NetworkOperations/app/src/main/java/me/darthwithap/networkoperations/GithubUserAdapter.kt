package me.darthwithap.networkoperations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_github_user.view.*

class GithubUserAdapter(private val githubUsers: ArrayList<GithubUser>) :
    RecyclerView.Adapter<GithubUserAdapter.GithubUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_github_user, parent, false)
        )
    }

    override fun getItemCount() = githubUsers.size

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        holder.bindView(githubUsers[position])
    }

    inner class GithubUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(githubUser: GithubUser) {
            with(itemView) {
                tvLogin.text = githubUser.login
                tvUrl.text = githubUser.htmlUrl
                tvScore.text = githubUser.score.toString()
                Picasso.get().load(githubUser.avatarUrl).into(civAvatar)
            }
        }
    }
}