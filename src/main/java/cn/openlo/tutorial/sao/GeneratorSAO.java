/**
*
*/
package cn.openlo.tutorial.sao;

import cn.openlo.exception.LOSException;

/**
 * <p>
 * Title: GeneratorSAO
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author gaozhi
 *
 * @version
 * @since 2016年5月31日
 */
public interface GeneratorSAO {

    public String generateContractNo() throws LOSException;
}
