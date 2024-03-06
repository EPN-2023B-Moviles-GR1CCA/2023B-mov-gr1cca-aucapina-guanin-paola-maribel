import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pryIIB_cine.DetallesPeliculaActivity
import com.example.pryIIB_cine.Pelicula
import com.example.pryIIB_cine.R  // Asegúrate de que el ID de tu proyecto esté correctamente importado
import com.google.android.gms.common.internal.ImagesContract.URL
import java.io.IOException
import java.io.InputStream
import java.net.URL


class PeliculaAdapter(private val peliculasList: List<Pelicula>) : RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {

    class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloTextView: TextView = itemView.findViewById(R.id.tituloTextView)
        val directorTextView: TextView = itemView.findViewById(R.id.directorTextView)
        val portadaImageView: ImageView = itemView.findViewById(R.id.portadaImageView)
        val sinopsisTextView: TextView=itemView.findViewById(R.id.sinopsisTextView)
        // Agrega otros TextView según sea necesario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula, parent, false)
        return PeliculaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculasList[position]

        holder.tituloTextView.text = pelicula.titulo
        holder.directorTextView.text = pelicula.director
        holder.sinopsisTextView.text=pelicula.director
        // Desactivar la política de red estricta para permitir la carga de imágenes desde el hilo principal (solo para fines de demostración, no recomendado para producción)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitNetwork().build())

        // Carga la imagen de la portada y establece en el ImageView
        try {
            val inputStream: InputStream = URL(pelicula.cover).openStream()
            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
            holder.portadaImageView.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            // Manejar el error al cargar la imagen
        }
        // Configura otros TextView según sea necesario


        holder.itemView.setOnClickListener {
            // Iniciar la nueva actividad y pasar los detalles de la película
            val intent = Intent(holder.itemView.context, DetallesPeliculaActivity::class.java)
            intent.putExtra("titulo", pelicula.titulo)
            intent.putExtra("director", pelicula.director)
            intent.putExtra("portada", pelicula.cover)
            intent.putExtra("sinopsis",pelicula.sinopsis)
            // Agrega otros detalles según sea necesario
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return peliculasList.size
    }
}
