package la.vamo.myapplication.WebServices.content;

import java.io.Serializable;

public class TesteSquidexInfo implements Serializable
{
    private Long total;
    private Item[] items;

    public Long getTotal()
    {
        return total;
    }

    public void setTotal(Long total)
    {
        this.total = total;
    }

    public Item[] getItems()
    {
        return items;
    }

    public void setItems(Item[] items)
    {
        this.items = items;
    }
    }