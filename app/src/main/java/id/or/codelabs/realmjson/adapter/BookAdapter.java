package id.or.codelabs.realmjson.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.or.codelabs.realmjson.R;
import id.or.codelabs.realmjson.model.Book;

/**
 * Created by FitriFebriana on 9/1/2016.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{
    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.txtKode.setText(book.getKdBuku());
        holder.txtJudul.setText(book.getJudulBuku());
        holder.txtPengarang.setText(book.getPengarang());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtKode;
        public TextView txtJudul;
        public TextView txtPengarang;

        public ViewHolder(View itemView) {
            super(itemView);
            txtKode = (TextView)itemView.findViewById(R.id.txt_kode);
            txtJudul = (TextView)itemView.findViewById(R.id.txt_judul);
            txtPengarang = (TextView)itemView.findViewById(R.id.txt_pengarang);
        }

    }


}
