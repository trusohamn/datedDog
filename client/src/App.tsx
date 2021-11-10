import React, { useEffect, useState } from 'react';
import './App.css';
import Graph from './components/Graph';
import { Service } from './types';

const server = 'http://localhost:8080/services';
function App() {
  const [services, setServices] = useState<Service[]>([]);
  const [newName, setNewName] = useState('');
  const [newUrl, setNewUrl] = useState('');

  useEffect(() => {
    fetch(server)
      .then((response) => response.json())
      .then((data) => console.log(data));
  }, []);

  const handleAddNewService = (e: React.FormEvent) => {
    e.preventDefault();
    fetch(server, {
      method: 'POST',
      mode: 'cors',
      body: JSON.stringify({ name: newName, url: newUrl }),
      headers: {
        'Content-Type': 'application/json',
      },
    });
  };

  return (
    <div className='App'>
      Monitor
      <section>
        Add new <br />
        name
        <form onSubmit={handleAddNewService}>
          <input value={newName} onChange={(e) => setNewName(e.target.value)} />
          <br />
          url
          <input value={newUrl} onChange={(e) => setNewUrl(e.target.value)} />
          <input type='submit' />
        </form>
      </section>
      <section>
        Visualisation
        {services.forEach((service) => (
          <Graph service={service} />
        ))}
      </section>
    </div>
  );
}

export default App;
