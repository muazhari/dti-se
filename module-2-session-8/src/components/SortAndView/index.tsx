import ViewButton from "./ViewButton.tsx";
import SortButton from "./SortButton.tsx";


const SortAndViewComponent: React.FC = () => {
    return (
        <div className="flex gap-4 text-base rounded-none text-slate-400 px-5 pt-4">
            <SortButton/>
            <ViewButton/>
        </div>
    )
}

export default SortAndViewComponent;
