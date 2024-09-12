import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listapersonas.R
import com.example.listapersonas.models.Contact

class ContactAdapter(
    private val contactList: ArrayList<Contact>,
    private val listener: OnContactClickListener
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val colorList = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.GRAY,
        Color.LTGRAY, Color.MAGENTA, Color.BLACK, Color.WHITE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.contacto_item_layout, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position], listener)
    }

    fun itemAdded(contact: Contact) {
        contactList.add(0, contact)
        notifyItemInserted(0)
    }

    fun itemDeleted(contact: Contact) {
        val index = contactList.indexOf(contact)
        contactList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun itemUpdated(contact: Contact) {
        val index = contactList.indexOf(contact)
        contactList[index] = contact
        notifyItemChanged(index)
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var lblContactItemPhone = itemView.findViewById<TextView>(R.id.lblContactItemPhone)
        private var btnEditContactItem = itemView.findViewById<ImageButton>(R.id.btnEditContactItem)
        private var btnDeleteContactItem = itemView.findViewById<ImageButton>(R.id.btnDeleteContactItem)
        private var btnColorContactItem = itemView.findViewById<ImageButton>(R.id.btnColorContactItem)

        fun bind(contact: Contact, listener: OnContactClickListener) {
            lblContactItemPhone.text = contact.phone
            lblContactItemPhone.setBackgroundColor(contact.backgroundColor)

            btnEditContactItem.setOnClickListener {
                listener.onContactEditClickListener(contact)
            }
            btnDeleteContactItem.setOnClickListener {
                listener.onContactDeleteClickListener(contact)
            }
            btnColorContactItem.setOnClickListener {
                val currentColor = contact.backgroundColor
                val newColor = colorList[(colorList.indexOf(currentColor) + 1) % colorList.size]
                lblContactItemPhone.setBackgroundColor(newColor)
                contact.backgroundColor = newColor
            }
        }
    }

    interface OnContactClickListener {
        fun onContactEditClickListener(contact: Contact)
        fun onContactDeleteClickListener(contact: Contact)
    }
}
