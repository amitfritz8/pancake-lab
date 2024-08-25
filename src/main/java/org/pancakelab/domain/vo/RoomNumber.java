package org.pancakelab.domain.vo;

public class RoomNumber {
    private String number;

    public RoomNumber(String number) {
        if (!isValidRoomNumber(number)) {
            throw new IllegalArgumentException("Invalid room number.");
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
