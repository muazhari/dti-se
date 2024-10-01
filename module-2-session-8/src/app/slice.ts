import {createSlice} from '@reduxjs/toolkit';


export interface PokemonState {
    searchQuery: string;
    sortByField: string;
    columnView: number;
}

const initialState: PokemonState = {
    searchQuery: '',
    sortByField: 'hp',
    columnView: 2,
};

export const pokemonSlice = createSlice({
    name: 'pokemon',
    initialState,
    reducers: {
        setSearchQuery: (state, action) => {
            state.searchQuery = action.payload;
        },
        setSortByField: (state, action) => {
            state.sortByField = action.payload;
        },
        setColumnView: (state, action) => {
            state.columnView = action.payload;
        },
    },
});
