package id.sch.smktelkom_mlg.katalogbukuramadhan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sch.smktelkom_mlg.katalogbukuramadhan.R;
import id.sch.smktelkom_mlg.katalogbukuramadhan.activity.Book;

/**
 * Created by Earl Kurnia on 16/04/2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> implements Filterable {
    private List<Book> books;
    private List<Book> booksOri;
    private Context mContext;
    private Filter filter;

    public BooksAdapter(Context context, List<Book> bookList) {
        this.books = bookList;
        this.booksOri = bookList;
        mContext = context;
    }

    public Filter getFilter() {
        if (filter == null)
            filter = new BooksFilter();
        return filter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = books.get(position);
        holder.txtBookTitle.setText(book.getBook_title());
        holder.txtISBN.setText(book.getISBN() + "");
        /*holder.txtOtherInfo.setText(book.getBook_author() + "-" + book.getPublished_year());
        if(book.getBook_cover() == 0)
            holder.imgBookCover.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(),
                    R.drawable.placeholder, null));
        else(book.getBook_cover() == 0)
            holder.imgBookCover.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(),
                    book.getBook_cover(), null));
        holder.click(book, position, listener); */
    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtISBN)
        TextView txtISBN;
        @BindView(R.id.txtBookTitle)
        TextView txtBookTitle;
        @BindView(R.id.txtOtherInfo)
        TextView txtOtherInfo;
        @BindView(R.id.imgBookCover)
        ImageView imgBookCover;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private class BooksFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults results = new FilterResults();
            String substr = constraint.toString().toLowerCase();
            if (substr == null || substr.length() == 0) {
                results.values = booksOri;
                results.count = booksOri.size();
            } else {
                final ArrayList<Book> nlist = new ArrayList<Book>();
                int count = booksOri.size();

                for (int i = 0; i < count; i++) {
                    final Book book = booksOri.get(i);
                    String value = "", value2 = "";
                    value = book.getBook_title().toLowerCase();
                    value2 = book.getBook_author().toLowerCase();
                    if (value.contains(substr) || value2.contains(substr)) {
                        nlist.add(book);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            books = (List<Book>) results.values;
            notifyDataSetChanged();
        }
    }

}
