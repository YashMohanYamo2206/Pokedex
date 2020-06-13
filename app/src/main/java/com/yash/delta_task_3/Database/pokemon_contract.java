package com.yash.delta_task_3.Database;

import android.provider.BaseColumns;

public class pokemon_contract {
    public pokemon_contract() {
    }

    public static final class PokemonEntry implements BaseColumns {
        public static final String TABLE_NAME = "pokemon";
        public static final String COLUMN_POKEMON_NAME = "pokemon_name";
        public static final String COLUMN_IMAGE_URL = "pokemon_image";
        public static final String COLUMN_STATS = "stats";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
