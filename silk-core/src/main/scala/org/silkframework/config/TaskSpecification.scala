package org.silkframework.config

import org.silkframework.entity.EntitySchema
import org.silkframework.util.Identifier

/**
  * Base trait of all task specifications.
  */
trait TaskSpecification {

  /**
    * The identifier for this task.
    */
  def id: Identifier

  /**
    * The schemata of the input data for this task.
    * A separate entity schema is returned for each input.
    */
  def inputSchemata: Seq[EntitySchema] = Seq.empty

  /**
    * The schema of the output data.
    * Returns None, if the schema is unknown or if no output is written by this task.
    */
  def outputSchema: Option[EntitySchema] = None

}
