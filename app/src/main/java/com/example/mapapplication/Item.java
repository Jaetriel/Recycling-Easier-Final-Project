package com.example.mapapplication;


public class Item {

    public enum ItemType
    {
        //override the toString methods of each member to display a whole string
        GLASS {
            @Override
            public String toString() {
                return "Glass";
            }
        },
        PLASTIC {
            @Override
            public String toString() {
                return "Plastic";
            }
        },
        METAL {
            @Override
            public String toString() {
                return "Metal";
            }
        },
        PAPER {
            @Override
            public String toString() {
                return "Paper";
            }
        },
        MIXED {
            @Override
            public String toString() {
                return "Mixed/Other";
            }
        }
    }

    public enum ItemSymbol
    {
        //override the toString methods of each member to display a whole string
        WIDELY {
            @Override
            public String toString() {
                return "Widely Recycled";
            }
        },
        CHECK{
            @Override
            public String toString() {
                return "Check Locally";
            }
        },
        NONRECYCLABLE{
            @Override
            public String toString() {
                return "Not recycled/Other";
            }
        }


    }


    private String id;// barcode
    private String name;// item name
    private String type;// packaging material
    private String symbol;// recycling symbol




    public Item()
    {}

    public Item(String id, String name, String type, String symbol)
    {

        this.id = id;
        this.name = name;
        this.type = type;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
