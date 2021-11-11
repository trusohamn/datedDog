import { Service } from '../types';

function Graph({ service }: { service: Service }) {
  return (
    <div style={{ display: 'flex', margin: 10 }}>
      {service.name}
      {service.url}
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
