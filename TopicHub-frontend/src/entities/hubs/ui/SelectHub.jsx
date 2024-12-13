import { useSelector } from "react-redux";
import MainSelect from "../../../shared/Select/ui/MainSelect";
import { getHubsList } from "../model/hubsSlice";

function SelectHub({handleChange}) {

    const list = [{id:0, name:"-"}]

    list.push(...useSelector(getHubsList).filter(item=>item.id!=0))
   
    return ( <MainSelect title="Выберите хаб" list={list} defaultValue={list[0].id} handleChange={handleChange} /> );
}

export default SelectHub;