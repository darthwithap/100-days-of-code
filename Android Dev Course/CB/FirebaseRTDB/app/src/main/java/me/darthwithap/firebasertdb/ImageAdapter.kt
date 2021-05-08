package me.darthwithap.firebasertdb

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_image.view.*

class ImageAdapter(private val uploads: List<Upload>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private var itemClickListener : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_image, parent, false
            )
        )
    }

    override fun getItemCount() = uploads.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(uploads[position])
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        init {
            with(itemView) {
                setOnClickListener(this@ImageViewHolder)
                setOnCreateContextMenuListener(this@ImageViewHolder)
            }
        }

        fun bind(upload: Upload) {
            with(itemView) {
                tvImageName.text = upload.name
                Picasso.get().load(upload.url)
                    .placeholder(R.drawable.ic_placeholder)
                    .fit()
                    .centerCrop()
                    .into(ivImage)
            }
        }

        override fun onClick(v: View?) {
            if (itemClickListener!=null) {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(pos)
                }
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.setHeaderTitle("Menu")
            menu?.add(Menu.NONE, 1, 1, "Other")?.setOnMenuItemClickListener(this@ImageViewHolder)
            menu?.add(Menu.NONE, 2, 2, "Delete")?.setOnMenuItemClickListener(this@ImageViewHolder)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            if (itemClickListener!=null) {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    when (item?.itemId) {
                        (1) -> itemClickListener?.onOtherClick(pos)
                        (2) -> itemClickListener?.onDeleteClick(pos)
                    }
                }
                return true
            }
            return false
        }
    }

    interface OnItemClickListener {
        fun onItemClick(pos: Int)
        fun onDeleteClick(pos: Int)
        fun onOtherClick(pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

}