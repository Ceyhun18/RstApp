package com.shadliq.palaces.enums;


import com.shadliq.palaces.MyApplication;
import com.shadliq.palaces.R;

public enum EventTypeEnum {

   ALL(0, R.string.all),  WEDDING(1,  R.string.wedding), BIRTHDAY(2, R.string.birthday), ENGAGEMENT(3, R.string.engagement), HENNA(4, R.string.henna);

    EventTypeEnum(int value, int strId) {
        this.value = (byte) value;
        this.strId = strId;
    }

    private byte value;
    private int strId;

    public static EventTypeEnum getEnum(Byte value) {
        for (EventTypeEnum typeEnum : values()) {
            if (typeEnum.value == (value)) {
                return typeEnum;
            }
        }
        return null;
    }

    public byte getValue() {
        return value;
    }

    public int getStrId(){
        return strId;
    }

    @Override
   public String toString() {
       return MyApplication.getContext().getString(strId);
   }
}
