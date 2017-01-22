
package io.github.kolacbb.kolaweibo.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Visible {

    @SerializedName("type")
    @Expose
    private long type;
    @SerializedName("list_id")
    @Expose
    private long listId;

    /**
     * 
     * @return
     *     The type
     */
    public long getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(long type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The listId
     */
    public long getListId() {
        return listId;
    }

    /**
     * 
     * @param listId
     *     The list_id
     */
    public void setListId(long listId) {
        this.listId = listId;
    }

}
