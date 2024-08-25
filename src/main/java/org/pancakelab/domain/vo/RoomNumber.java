package org.pancakelab.domain.vo;

import org.pancakelab.domain.exception.PancakeLabException;
import org.pancakelab.domain.exception.PancakeLabExceptionEnum;

public class RoomNumber {
    private String number;

    public RoomNumber(String number) {
        if (!isValidRoomNumber(number)) {
            throw new PancakeLabException(PancakeLabExceptionEnum.INVALID_DATA, "Invalid room number.");
        }
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    private boolean isValidRoomNumber(String number) {
        return true;
    }
}
