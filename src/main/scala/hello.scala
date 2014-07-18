import java.io.File
import net.sf.samtools.SAMFileReader
import net.sf.samtools.SAMRecord
import net.sf.samtools.SAMRecordIterator
import net.sf.samtools.SAMFileReader.ValidationStringency


object BamReader {
  def averageBaseQual(base: Array[Byte]): Int = {
    var sum = 0
    for (x <- base) {
      sum += x
    }
    sum / base.length
  }

  def averageMapQual(base: List[Int]): Int = {
    base.sum / base.length
  }

  def main(args: Array[String]): Unit = {

    val inFile = "target/test.bam"
    val inputSam: SAMFileReader = new SAMFileReader(new File(inFile))

    inputSam.setValidationStringency(ValidationStringency.SILENT)
    val iter: SAMRecordIterator = inputSam.iterator()

    var all: List[Int] = List()

    while (iter.hasNext()) {
      val rec: SAMRecord = iter.next()
      if (!rec.getReadUnmappedFlag()) {
        var refName = rec.getReferenceName()
        var alignStart = rec.getAlignmentStart()
        var alignEnd = rec.getAlignmentEnd()
        var mapQuality = rec.getMappingQuality()
        
        var cigarString = rec.getCigarString()
        var readGroup = rec.getReadGroup()
        var header = rec.getHeader()
        var readString =  rec.getReadString()
        //println(readString)

        var base = rec.getBaseQualities()
        var aveBq = averageBaseQual(base)

        all =  mapQuality :: all

        //println(rec.getReferencePositionAtReadPosition(1))
        println(f"$refName%s, $alignStart%d, $alignEnd%d")
      }
    }

    println(s"Average mapping quality: ${averageMapQual(all)}" )

    iter.close()
    inputSam.close()
  }
}
