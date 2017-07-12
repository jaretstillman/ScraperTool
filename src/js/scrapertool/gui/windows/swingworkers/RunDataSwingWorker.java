package js.scrapertool.gui.windows.swingworkers;

import java.util.List;

import javax.swing.SwingWorker;

public class RunDataSwingWorker<T,V> extends SwingWorker<T,V>
{	
	public void pub(V s)
	{
		super.publish(s);
	}
	
	@Override
	protected void process(List<V> stuff)
	{
		super.process(stuff);
	}
	
	@Override
	protected void done()
	{
		super.done();
	}

	@Override
	protected T doInBackground() throws Exception
	{
		return null;
	}
}
