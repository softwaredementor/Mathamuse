package com.appfission.utils;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */

public enum Operator {

        PLUS("+"), MINUS("-"), MULTIPLIER("*"), DIVIDER("/");
        private String displayValue;

        private Operator(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
}

