class CreateLocations < ActiveRecord::Migration
  def change
    create_table :locations do |t|
      t.string :faculty
      t.string :name

      t.timestamps
    end
  end
end
