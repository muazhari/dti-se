import usePokemonList from "../../hooks/usePokemonList.ts";
import {Button, Dropdown, DropdownItem, DropdownMenu, DropdownTrigger} from "@nextui-org/react";

const SortButton: React.FC = () => {
    const {sortByField, setSortByField} = usePokemonList();

    const fieldNames = {
        "": "None",
        hp: "Health",
    }

    return (
        <Dropdown className="w-[18.7rem] bg-slate-600 text-base text-slate-300">
            <DropdownTrigger>
                <Button
                    className="flex-[4] flex px-3 py-2 rounded-lg justify-between bg-slate-600 text-base text-slate-300">
                    Sort by {fieldNames[sortByField]}
                    <img
                        loading="lazy"
                        src="https://cdn.builder.io/api/v1/image/assets/TEMP/8c812e171f74391ed2b865dc7c976323b2dd7dda0e605fb6659cda2c97083676?apiKey=e946ad7206da4373a899cf38e79aca51&"
                        className="object-contain w-[1rem]" alt=""
                    />
                </Button>
            </DropdownTrigger>
            <DropdownMenu>
                {
                    Object.entries(fieldNames).map(([field, name], index) => (
                        <DropdownItem key={index} onClick={() => setSortByField(field)}>{name}</DropdownItem>
                    ))
                }
            </DropdownMenu>
        </Dropdown>
    )
}

export default SortButton;
