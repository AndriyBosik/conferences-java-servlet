package com.conferences.helper;

import java.util.List;

public class PermissionsHelper {

    private PermissionsHelper() {}

    public static class Builder {

        private PermissionsHelper helper;
        private List<String> urls;
        private List<String> roles;

        public Builder init() {
            helper = new PermissionsHelper();
            return this;
        }

        public Builder urlPatterns(String... urls) {
            for (String url: urls) {
                this.urls.add(url);
            }
            return this;
        }

        public Builder allowAll(String... roles) {
            return this;
        }

        public Builder denyAll(String... roles) {
            return this;
        }

        public PermissionsHelper build() {
            return helper;
        }

    }

}
