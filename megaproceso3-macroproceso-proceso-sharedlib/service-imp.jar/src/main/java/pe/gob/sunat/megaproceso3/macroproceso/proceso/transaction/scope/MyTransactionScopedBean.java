package pe.gob.sunat.megaproceso3.macroproceso.proceso.transaction.scope;

import java.io.Serializable;

import javax.transaction.TransactionScoped;

@TransactionScoped
public class MyTransactionScopedBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getId() {
        return this + "";
    }
}
