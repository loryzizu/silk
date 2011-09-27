package de.fuberlin.wiwiss.silk.plugins

import datasource._
import transformer._
import aggegrator._
import writer._
import metric._
import de.fuberlin.wiwiss.silk.datasource.DataSource
import de.fuberlin.wiwiss.silk.linkspec.input.Transformer
import de.fuberlin.wiwiss.silk.linkspec.similarity.{Aggregator, DistanceMeasure}
import de.fuberlin.wiwiss.silk.output.{LinkWriter, Formatter}
import java.io.File
import de.fuberlin.wiwiss.silk.util.Timer
import java.util.logging.Logger

/**
 * Registers all default plugins.
 */
object Plugins {
  private var registered = false

  private implicit val logger = Logger.getLogger(Plugins.getClass.getName)

  def register(pluginsDir: File = new File(System.getProperty("user.home") + "/.silk/plugins/")): Unit = synchronized {
    if(registered) return

    DataSource.register(classOf[SparqlDataSource])
    DataSource.register(classOf[CacheDataSource])

    Transformer.register(classOf[ReplaceTransformer])
    Transformer.register(classOf[RegexReplaceTransformer])
    Transformer.register(classOf[ConcatTransformer])
    Transformer.register(classOf[RemoveBlanksTransformer])
    Transformer.register(classOf[LowerCaseTransformer])
    Transformer.register(classOf[UpperCaseTransformer])
    Transformer.register(classOf[NumReduceTransformer])
    Transformer.register(classOf[StemmerTransformer])
    Transformer.register(classOf[StripPrefixTransformer])
    Transformer.register(classOf[StripPostfixTransformer])
    Transformer.register(classOf[StripUriPrefixTransformer])
    Transformer.register(classOf[AlphaReduceTransformer])
    Transformer.register(classOf[RemoveSpecialCharsTransformer])
    Transformer.register(classOf[LogarithmTransformer])
    Transformer.register(classOf[ConvertCharsetTransformer])
    Transformer.register(classOf[FilterValues])
    Transformer.register(classOf[RemoveEmptyValues])
    Transformer.register(classOf[Tokenizer])
    Transformer.register(classOf[MergeTransformer])

    DistanceMeasure.register(classOf[LevenshteinMetric])
    DistanceMeasure.register(classOf[LevenshteinDistance])
    DistanceMeasure.register(classOf[JaroDistanceMetric])
    DistanceMeasure.register(classOf[JaroWinklerDistance])
    DistanceMeasure.register(classOf[QGramsMetric])
    DistanceMeasure.register(classOf[EqualityMetric])
    DistanceMeasure.register(classOf[InequalityMetric])
    DistanceMeasure.register(classOf[NumMetric])
    DistanceMeasure.register(classOf[DateMetric])
    DistanceMeasure.register(classOf[DateTimeMetric])
    DistanceMeasure.register(classOf[GeographicDistanceMetric])
    DistanceMeasure.register(classOf[JaccardDistance])
    DistanceMeasure.register(classOf[DiceCoefficient])
    DistanceMeasure.register(classOf[TokenwiseStringDistance])

    Aggregator.register(classOf[AverageAggregator])
    Aggregator.register(classOf[MaximumAggregator])
    Aggregator.register(classOf[MinimumAggregator])
    Aggregator.register(classOf[QuadraticMeanAggregator])
    Aggregator.register(classOf[GeometricMeanAggregator])

    LinkWriter.register(classOf[FileWriter])
    LinkWriter.register(classOf[SparqlWriter])
    LinkWriter.register(classOf[MemoryWriter])

    Formatter.register(classOf[NTriplesFormatter])
    Formatter.register(classOf[AlignmentFormatter])

    //Register external plugins
    Timer("Registering external plugins") {
      if(pluginsDir.isDirectory) {
        DataSource.registerJars(pluginsDir)
        Transformer.registerJars(pluginsDir)
        DistanceMeasure.registerJars(pluginsDir)
        Aggregator.registerJars(pluginsDir)
      }
      else {
       logger.info("No plugins loaded because the plugin directory " + pluginsDir + " has not been found.")
      }
    }

    registered = true
  }
}