package jp.dodododo.dao.sql.node;

import jp.dodododo.dao.context.CommandContext;

public interface Node {

	int getChildSize();

	boolean isEmpty();

	Node getChild(int index);

	void addChild(Node node);

	void accept(CommandContext ctx);

	void setSql(String sql);

	String getSql();

	void setParent(Node parent);

}
