import usePokemonList from "../../hooks/usePokemonList.ts";

const ViewButton: React.FC = () => {
    const {setColumnView} = usePokemonList();

    return (
        <div className="flex-1 flex">
            <button
                className="flex justify-center items-center p-3 bg-slate-900 h-10 w-10 rounded-l-lg"
                onClick={() => setColumnView(1)}
            >
                <div className="bg-slate-400 h-3/4 w-3/4"/>
            </button>
            <div className="w-0.5 h-10 bg-slate-400"/>
            <button
                className="grid grid-cols-2 grid-rows-2 items-center h-10 w-10 p-3 bg-slate-600 gap-0.5 rounded-r-lg"
                onClick={() => setColumnView(2)}
            >
                <div className="bg-slate-400 h-full w-full"/>
                <div className="bg-slate-400 h-full w-full"/>
                <div className="bg-slate-400 h-full w-full"/>
                <div className="bg-slate-400 h-full w-full"/>
            </button>
        </div>
    );
}

export default ViewButton;
