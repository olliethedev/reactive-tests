package com.beastpotato.reactivetests.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MoviesData extends BaseObservable implements Parcelable {

    @SerializedName("dates")
    @Expose
    private Dates dates;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("results")
    @Expose
    private List<Movie> results;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public Dates getDates() {
        return dates;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public String toString() {
        return
                "MoviesData{" +
                        "dates = '" + dates + '\'' +
                        ",page = '" + page + '\'' +
                        ",total_pages = '" + totalPages + '\'' +
                        ",results = '" + results + '\'' +
                        ",total_results = '" + totalResults + '\'' +
                        "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.dates, flags);
        dest.writeInt(this.page);
        dest.writeInt(this.totalPages);
        dest.writeList(this.results);
        dest.writeInt(this.totalResults);
    }

    public MoviesData() {
    }

    protected MoviesData(Parcel in) {
        this.dates = in.readParcelable(Dates.class.getClassLoader());
        this.page = in.readInt();
        this.totalPages = in.readInt();
        this.results = new ArrayList<Movie>();
        in.readList(this.results, List.class.getClassLoader());
        this.totalResults = in.readInt();
    }

    public static final Parcelable.Creator<MoviesData> CREATOR = new Parcelable.Creator<MoviesData>() {
        public MoviesData createFromParcel(Parcel source) {
            return new MoviesData(source);
        }

        public MoviesData[] newArray(int size) {
            return new MoviesData[size];
        }
    };
}