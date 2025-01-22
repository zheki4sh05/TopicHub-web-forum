import { useSelector } from "react-redux";
import MainSelect from "../../../shared/Select/ui/MainSelect";
import { getHubsList } from "../model/hubsSlice";
import { getHub } from "../../../features/Sanbox/model/sandboxSlice";
import { getActiveLanguage } from "../../../processes/header/model/settingsSlice";

function SelectHub({handleChange}) {
    const lng = useSelector(getActiveLanguage)
    const list = [{id:0, name:"-"}]
    const activeHub = useSelector(getHub)
    list.push(...useSelector(getHubsList).filter(item=>item.id!=0).map(item=>{return {...item, name:item[lng]}}) )
   
    return ( <MainSelect title="Выберите хаб" list={list} defaultValue={activeHub!=null ? activeHub : list[0].id} handleChange={handleChange} /> );
}

export default SelectHub;