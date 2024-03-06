// FuncionAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pryIIB_cine.Funcion
import com.example.pryIIB_cine.R

class FuncionAdapter(private val funcionesList: List<Funcion>) :
    RecyclerView.Adapter<FuncionAdapter.FuncionViewHolder>() {

    class FuncionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nomSalaTextView)
        val horaTextView: TextView = itemView.findViewById(R.id.horarioTextView)
        val tipoTextView: TextView=  itemView.findViewById(R.id.tipoTextView)
        val idiomaTextView: TextView= itemView.findViewById(R.id.idiomaTextView)
        // Agrega otros TextView según sea necesario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuncionViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_funcion, parent, false)
        return FuncionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FuncionViewHolder, position: Int) {
        val funcion = funcionesList[position]

        holder.nombreTextView.text = funcion.nomSala
        holder.horaTextView.text = funcion.horario
        holder.tipoTextView.text=funcion.tipo
        holder.idiomaTextView.text=funcion.idioma
        // Configura otros TextView según sea necesario
    }

    override fun getItemCount(): Int {
        return funcionesList.size
    }
}
