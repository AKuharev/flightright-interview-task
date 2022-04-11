import Papa from "papaparse";
import {useCallback, useEffect, useState} from "react";
import axios from "axios";

function App() {
    const [chunk, setChunk] = useState([]);
    const [result, setResult] = useState(0);
    const [sources, setSources] = useState([]);

    const changeHandler = (event) => {
        Papa.parse(event.target.files[0], {
            worker: true,
            skipEmptyLines: true,
            header: true,
            chunkSize: 1024 * 1000,
            chunk: function (results) {
                setChunk(results.data);
            }
        });
    };

    const sendChunk = useCallback(async () => {
        const response = await axios.post(
            'http://localhost:8080/csv',
            chunk,
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        )
        setResult(response.status);
    }, [])

    const getSources = useCallback(async () => {
        const response = await axios.get('http://localhost:8080/csv');
        setSources(response.data);
    }, [])

    useEffect(() => {
        sendChunk()
            .catch(console.error);

        getSources()
            .catch(console.error);
    }, [])

    return (
        <div>
            {/* File Uploader */}
            <input
                type="file"
                name="file"
                accept=".csv"
                onChange={changeHandler}
                style={{display: "block", margin: "10px auto"}}
            />
            <p>
                Status : {result}
            </p>
            <table>
                <tbody>
                {sources.map(source => (
                    <tr>
                        {Object.values(source).map(val => (
                            <td>{val}</td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default App;
