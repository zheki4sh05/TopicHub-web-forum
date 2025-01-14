import { useSelector } from "react-redux";
import MainSelect from "../../../shared/Select/ui/MainSelect";
import { getHubsList } from "../model/hubsSlice";
import { getHub } from "../../../features/Sanbox/model/sandboxSlice";

function SelectHub({handleChange}) {

    const list = [{id:0, name:"-"}]
    const activeHub = useSelector(getHub)
    list.push(...useSelector(getHubsList).filter(item=>item.id!=0))
   
    return ( <MainSelect title="Выберите хаб" list={list} defaultValue={activeHub!=null ? activeHub : list[0].id} handleChange={handleChange} /> );
}

export default SelectHub;