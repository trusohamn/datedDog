import { Service } from '../types';

function Graph({ service }: { service: Service }) {
  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        margin: 10,
        alignItems: 'center',
        backgroundColor: 'lightGray',
        padding: 15,
      }}
    >
      <h2>{service.name}</h2>
      <h3>{service.url}</h3>
      <div style={{ display: 'flex' }}>
        {service.status.map((healthy) => (
          <div
            style={{ padding: 10, backgroundColor: healthy ? 'green' : 'red' }}
          ></div>
        ))}
      </div>
    </div>
  );
}

export default Graph;
