import { useSelector } from "react-redux";
import MainSelect from "../../../shared/Select/ui/MainSelect";
import { getHubs } from "../../../pages/Article/model/feedSlice";

function SelectHub({handleChange}) {

    const list = [{id:0, name:"-"}]

    list.push(...useSelector(getHubs).filter(item=>item.id!=0))
    console.log(list)
    return ( <MainSelect title="Выберите хаб" list={list} defaultValue={list[0].id} handleChange={handleChange} /> );
}

export default SelectHub;