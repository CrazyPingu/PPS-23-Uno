package view.settings

import view.Label

import java.awt.{Color, Dimension}
import javax.swing.{JTable, SwingConstants}
import javax.swing.table.{DefaultTableCellRenderer, DefaultTableModel, TableColumn}

/**
 * The Table class represents a custom JTable with specific configurations for displaying data.
 *
 * @param data        The data to be displayed in the table.
 * @param columnNames The names of the columns.
 * @param columnSize  The preferred widths of the columns.
 */
class Table(data: Array[Array[AnyRef]], columnNames: Array[AnyRef], columnSize: Array[Int])
    extends JTable(new DefaultTableModel(data, columnNames)):

  for i <- columnSize.indices do
    val column: TableColumn = this.getColumnModel.getColumn(i)
    column.setPreferredWidth(columnSize(i))

  setFocusable(false)
  setEnabled(false)
  setFont(getFont.deriveFont(20f))
  setIntercellSpacing(new Dimension(7, 7))
  setRowHeight(getRowHeight() + 20)
  applyPadding()

  /**
   * Applies padding and custom rendering to the table cells.
   */
  private def applyPadding(): Unit =
    val renderer = new DefaultTableCellRenderer:

      /**
       * Customizes the rendering of table cells.
       *
       * @param table      The JTable that uses this renderer.
       * @param value      The value to assign to the cell at [row, column].
       * @param isSelected True if the cell is selected.
       * @param hasFocus   True if the cell has focus.
       * @param row        The row of the cell to render.
       * @param column     The column of the cell to render.
       * @return The component used for drawing the cell.
       */
      override def getTableCellRendererComponent(
        table: JTable,
        value: Any,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
      ): java.awt.Component =
        val component = new Label(value.toString)
        value match
          case "Achieved" =>
            component.setForeground(Color.GREEN)
          case "Not achieved" =>
            component.setForeground(Color.RED)
          case _ =>
            component.setBackground(Color.WHITE)
            component.setForeground(Color.BLACK)
        component.setHorizontalAlignment(SwingConstants.CENTER)
        component

    for i <- 0 until getColumnCount do getColumnModel.getColumn(i).setCellRenderer(renderer)
