package com.jacob.newsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class Pagination {

    @Expose
    @SerializedName("limit")
    private int limit;

    @Expose
    @SerializedName("offset")
    private int offset;

    @Expose
    @SerializedName("count")
    private int count;

    @Expose
    @SerializedName("total")
    private int total;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "Pagination{limit=%d, offset=%d, count=%d, total=%d}",
                limit,
                offset,
                count,
                total);
    }
}
